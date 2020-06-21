import React, {Component} from "react";
import {fade, makeStyles, withStyles} from "@material-ui/core";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import Paper from "@material-ui/core/Paper";
import {getAuthorsString} from "../../util/Utils";
import IconButton from "@material-ui/core/IconButton";
import {Delete} from "@material-ui/icons";
import {trackPromise} from "react-promise-tracker";
import {deleteBook} from "../../util/APIUtils";

class Column {
    /**
     * @param {string} id
     * @param {string} label
     * @param {boolean} numeric
     * @param {boolean} disablePadding
     */
    constructor(id, label, numeric, disablePadding) {
        this.id = id;
        this.label = label;
        this.numeric = numeric;
        this.disablePadding = disablePadding;

        this.width = null;
    }

    /**
     * @param {number} value
     */
    addWidth(value) {
        this.width = value;
        return this;
    }
}

const headCells = new Map([
    new Column('name', 'Название', true, false)
        .addWidth(200),
    new Column('genre', 'Жанр', false, false)
        .addWidth(100),
    new Column('lang', 'Язык', false, false)
        .addWidth(60),
    new Column('authors', 'Авторы', false, false)
        .addWidth(200)
].map(entry => [entry.id, entry]));

class BooksTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            order: 'asc',
            orderBy: ''
        };
    }

    handleRequestSort = (event, property) => {
        const {order, orderBy} = this.state;
        const isAsc = orderBy === property && order === 'asc';
        this.setState(() => ({
            order: isAsc ? 'desc' : 'asc',
            orderBy: property
        }));
    };

    deleteBook = (bookId) => {
        if (!window.confirm("Вы действительно хотите удалить книгу?")) {
            return;
        }
        trackPromise(
            deleteBook(bookId).then(r => {
                this.props.updateList()
            }).catch(error => {
                if (error.response.status === 400) {
                    alert(error.response.data.message);
                }
            })
        );
    };

    render() {
        const {classes, data, openBook} = this.props;
        const {order, orderBy} = this.state;
        return (
            <Paper className={classes.root}>
                <TableContainer className={classes.container}>
                    <Table
                        stickyHeader
                        aria-label="sticky table"
                        aria-labelledby="tableTitle"
                        size={'medium'}
                        style={{width: "100%", tableLayout: "auto"}}
                        className={classes.table}
                    >
                        <EnhancedTableHead
                            order={order}
                            orderBy={orderBy}
                            onRequestSort={this.handleRequestSort}
                            rowCount={data.length}
                        />
                        <TableBody>
                            {stableSort(data, getComparator(order, orderBy))
                                .map((row, index) => {
                                    const labelId = `enhanced-table-checkbox-${index}`;
                                    return (
                                        <TableRow
                                            hover
                                            classes={{selected: classes.selected}}
                                            className={classes.tableRow}
                                            role="checkbox"
                                            onDoubleClick={(event) => openBook(row)}
                                            tabIndex={-1}
                                            key={row.bookId}
                                        >
                                            <TableCell
                                                className={classes.tableCell}
                                                id={labelId}
                                            >{row.name}</TableCell>
                                            <TableCell
                                                className={classes.tableCell}
                                                style={{maxWidth: headCells.get('genre').width}}
                                            >{row.genre.name}</TableCell>
                                            <TableCell
                                                className={classes.tableCell}
                                                style={{maxWidth: headCells.get('lang').width}}
                                            >{row.lang.name}</TableCell>
                                            <TableCell
                                                className={classes.tableCell}
                                                style={{maxWidth: headCells.get('authors').width}}
                                            >{getAuthorsString(row.authors)}</TableCell>
                                            <TableCell
                                                className={classes.tableCell}
                                                style={{maxWidth: headCells.get('authors').width}}
                                            >
                                                <IconButton aria-label="delete" color="primary"
                                                            onClick={() => this.deleteBook(row.bookId)}>
                                                    <Delete/>
                                                </IconButton>
                                            </TableCell>
                                        </TableRow>
                                    );
                                })}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Paper>
        );
    }
}

function stableSort(array, comparator) {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
        const order = comparator(a[0], b[0]);
        if (order !== 0) return order;
        return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
}

function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

function getComparator(order, orderBy) {
    return order === 'desc'
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy);
}

function EnhancedTableHead(props) {
    const classes = makeStyles((theme) => ({
        cell: {
            width: '100px',
            fontSize: 12,
            lineHeight: '16px',
            color: 'rgba(0, 0, 0, 0.5)'
        },
        delete: {
            width: '20px',
        }
    }))();

    // eslint-disable-next-line no-unused-vars
    const {order, orderBy, rowCount, onRequestSort} = props;
    const createSortHandler = (property) => (event) => {
        onRequestSort(event, property);
    };

    return (
        <TableHead>
            <TableRow>
                {[...headCells.values()].map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        align={'left'}
                        padding={headCell.disablePadding ? 'none' : 'default'}
                        sortDirection={orderBy === headCell.id ? order : false}
                        className={classes.cell}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}
                        >
                            {headCell.label}
                        </TableSortLabel>
                    </TableCell>
                ))}
                <TableCell key="delete" className={classes.delete}/>
            </TableRow>
        </TableHead>
    );
}

const styles = theme => ({
    root: {
        width: '100%',
        height: '100%',
    },
    container: {
        width: '100%',
        maxHeight: '100%',
        height: '100%'
    },
    tableRow: {
        '&:nth-child(odd)': {
            backgroundColor: 'rgba(0, 0, 0, 0.05)',
            '&:hover': {
                backgroundColor: 'rgba(0, 0, 0, 0.1)'
            }
        },
        '&$selected': {
            backgroundColor: fade(theme.palette.primary.main, 0.1),
            '&:hover': {
                backgroundColor: fade(theme.palette.primary.main, 0.15)
            },
            '&:nth-child(odd)': {
                backgroundColor: fade(theme.palette.primary.main, 0.2),
                '&:hover': {
                    backgroundColor: fade(theme.palette.primary.main, 0.25)
                }
            },
        }
    },
    tableCell: {
        padding: theme.spacing(2),
        fontSize: 12,
        whiteSpace: 'nowrap',
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        '$selected &': {
            color: theme.palette.primary.main
        }
    },
    checkbox: {},
    selected: {}
});

export default withStyles(styles)(BooksTable);