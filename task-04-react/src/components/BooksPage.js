import React, {Component} from "react";
import {Button, withStyles} from "@material-ui/core";
import BooksTable from "./table/BooksTable";
import {bookSearch, loadAuthors, loadGenres, loadLangs} from "../util/APIUtils";
import {trackPromise} from 'react-promise-tracker';
import BookEditDialog from "./BookEditDialog";
import {Book} from "../classes/Book";
import {NoteAdd, Update} from "@material-ui/icons";

class BooksPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            searchEnabled: true,
            search: '',
            authors: [],
            genres: [],
            langs: [],
            dialogOpen: false,
            newBook: false,
            currentBook: new Book(null, '', [], {}, {})
        };
    }

    search = () => {
        const {search} = this.state;
        this.setState({
            searchEnabled: false
        });
        trackPromise(
            bookSearch(search)
                .then(response => {
                    this.setState({
                        data: response.data,
                        searchEnabled: true
                    });

                }).catch(error => {
                console.log(error);
            })
        );
    };

    openBook = (book) => {
        this.setState({
            dialogOpen: true,
            currentBook: book,
            newBook: false
        });
    };

    createBook = () => {
        this.setState({
            dialogOpen: true,
            currentBook: new Book(null, '', [], {}, {}),
            newBook: true
        });
    };

    closeDialog = () => {
        this.setState({
            dialogOpen: false
        });
    };

    componentDidMount() {
        loadGenres().then(response => {
            this.setState(prevState => ({
                genres: response.data
            }));
        });
        loadAuthors().then(response => {
            this.setState(prevState => ({
                authors: response.data
            }));
        });
        loadLangs().then(response => {
            this.setState(prevState => ({
                langs: response.data
            }));
        });
        this.search();
    }

    render() {
        const {classes} = this.props;
        const {data, searchEnabled, genres, dialogOpen, newBook, currentBook, langs, authors} = this.state;
        return (
            <div className={classes.container}>
                <div className={classes.search}>
                    <div className={classes.footer}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={this.createBook}
                            startIcon={<NoteAdd />}
                        >
                            Добавить книгу
                        </Button>
                        <Button
                            disabled={!searchEnabled}
                            onClick={this.search}
                            className={classes.button}
                            startIcon={<Update />}
                        >Обновить список</Button>
                    </div>
                </div>
                <div className={classes.table}>
                    <BooksTable data={data} openBook={this.openBook} updateList={this.search}/>
                </div>
                <BookEditDialog open={dialogOpen}
                                genres={genres}
                                langs={langs}
                                authors={authors}
                                newBook={newBook}
                                bookId={currentBook.bookId}
                                close={this.closeDialog}
                                updateList={this.search}
                />
            </div>
        );
    }
}

const styles = theme => ({
    container: {
        width: '100%',
        display: 'grid',
        gridTemplateColumns: 'max-content 1fr',
        overflow: 'hidden'
    },
    search: {
        width: 272,
        padding: 16,
        gap: '20px',
        boxSizing: 'border-box',
        display: 'grid',
        gridAutoRows: 'max-content',
        background: '#ffffff',
        overflowY: 'auto'
    },
    table: {
        width: '100%',
        height: '100%',
        padding: 16,
        boxSizing: 'border-box',
        flexGrow: 1,
        display: 'flex',
        flexDirection: 'column',
        overflow: 'hidden'
    },
    label: {
        marginBottom: theme.spacing(1),
        letterSpacing: 0.25,
        fontSize: 14,
        color: 'rgba(0, 0, 0, 0.87)'
    },
    inputWrapper: {
        padding: 0,
        height: 30
    },
    input: {
        height: '100%',
        padding: theme.spacing(0, 2),
        fontSize: 14
    },
    button: {
        paddingTop: 20,
        color: theme.palette.primary.main
    },
    footer: {
        textAlign: 'center'
    }
});

export default withStyles(styles)(BooksPage);