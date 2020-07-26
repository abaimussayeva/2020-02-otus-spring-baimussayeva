import React, {Component} from "react";
import {withStyles} from "@material-ui/core";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from "@material-ui/core/Button";
import TreeObjectChooser from "./table/TreeObjectChooser";
import {Book} from "../classes/Book";
import {createBook, loadBook, saveBook} from "../util/APIUtils";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import Select from "@material-ui/core/Select";
import Input from "@material-ui/core/Input";
import MenuItem from "@material-ui/core/MenuItem";
import Checkbox from "@material-ui/core/Checkbox";
import ListItemText from "@material-ui/core/ListItemText";
import {getAuthorsString} from "../util/Utils";
import {trackPromise} from "react-promise-tracker";
import TextField from "@material-ui/core/TextField";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

class BookEditDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            book: new Book(null, '', [], {}, {})
        };
    }

    handleClose = () => {
        this.props.close();
    };

    handleSave = () => {
        const {book} = this.state;
        if (book.bookId === null) {
            trackPromise(
                createBook(book).then(r => {
                    this.props.close();
                    this.props.updateList()
                }).catch(error => {
                    if (error.response.status === 400) {
                        alert(error.response.data.message);
                    }
                    if (error.response.status === 500) {
                        alert("Ошибка сохранения книги");
                    }
                })
            );
        } else {
            trackPromise(
                saveBook(book).then(r => {
                    this.props.close();
                    this.props.updateList()
                }).catch(error => {
                    if (error.response.status === 400) {
                        alert(error.response.data.message);
                    }
                    if (error.response.status === 500) {
                        alert("Ошибка сохранения книги");
                    }
                })
            );
        }
    };

    handleGenreChange = (currentObject) => {
        this.setState(prevState => ({
            book: {
                ...prevState.book,
                genre: currentObject
            }
        }));
    };

    handleChange = (event) => {
        const value = event.target.value;
        this.setState(prevState => ({
            book: {
                ...prevState.book,
                name: value
            }
        }));
    };

    handleAuthorChange = (event) => {
        this.setState(prevState => ({
            book: {
                ...prevState.book,
                authors: event.target.value
            }
        }));
    };

    handleLangChange = (event) => {
        this.setState(prevState => ({
            book: {
                ...prevState.book,
                lang: event.target.value
            }
        }));
    };

    render() {
        const {classes, open, newBook, genres, bookId, authors, langs} = this.props;
        const {book} = this.state;
        if (bookId !== book.bookId) {
            if (bookId !== null) {
                loadBook(bookId).then(response => {
                    this.setState(prevState => ({
                        book: response.data
                    }));
                });
            } else {
                this.setState(prevState => ({
                    book: new Book(null, '', [], {}, {})
                }));
            }
        }

        return (
            <Dialog open={open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">{newBook ? "Новая книга" : "Редактирование"}</DialogTitle>
                <DialogContent className={classes.root}>
                    <FormControl fullWidth={true} className={classes.labelInput}>
                        <FormLabel htmlFor="name">{"Название книги"}</FormLabel>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="name"
                            fullWidth
                            value={book.name}
                            onChange={this.handleChange}
                        />
                    </FormControl>
                    <FormControl fullWidth={true} className={classes.labelInput}>
                        <FormLabel htmlFor="genre">{"Жанр"}</FormLabel>
                        <TreeObjectChooser
                            id="genre"
                            data={genres}
                            currentObject={book.genre}
                            enabled={true}
                            rootName={"Жанры"}
                            selectObject={this.handleGenreChange}/>
                    </FormControl>
                    <FormControl fullWidth={true} className={classes.labelInput}>
                        <FormLabel htmlFor="author">{"Автор"}</FormLabel>
                        <Select
                            id="author"
                            multiple
                            value={book.authors}
                            onChange={this.handleAuthorChange}
                            input={<Input/>}
                            renderValue={(selected) => getAuthorsString(selected)}
                            MenuProps={MenuProps}
                        >
                            {authors.map((author) => (
                                <MenuItem key={author.authorId} value={author}>
                                    <Checkbox checked={
                                        book.authors.some(e => e.authorId === author.authorId)}/>
                                    <ListItemText primary={author.name}/>
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <FormControl fullWidth={true} className={classes.labelInput}>
                        <FormLabel htmlFor="lang">{"Язык"}</FormLabel>
                        <Select
                            id="lang"
                            value={book.lang}
                            onChange={this.handleLangChange}
                            renderValue={(selected) => selected.name}
                        >
                            {langs.map((lang) => (
                                <MenuItem key={lang.langId} value={lang}>
                                    {lang.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose} color="primary">
                        Закрыть
                    </Button>
                    <Button onClick={this.handleSave} color="primary">
                        Сохранить
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }
}


const styles = theme => ({
    root: {
        width: 350
    },
    labelInput: {
        marginBottom: 15
    }
});

export default withStyles(styles)(BookEditDialog);
