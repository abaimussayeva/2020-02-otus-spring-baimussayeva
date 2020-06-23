import axios from "axios";

const get = (url) => {
    return axios.get(url,
        {
            headers: {
                'Content-Type': 'application/json'
            }
        });
};

export function loadBook(bookId) {
    return get('/api/book/' + bookId);
}

export function bookSearch(name) {
    return get('/api/book');
}

export function loadGenres() {
    return get('/api/genre');
}

export function loadAuthors() {
    return get('/api/author');
}

export function loadLangs() {
    return get('/api/lang');
}

export function createBook(book) {
    return axios.post('/api/book/',
        book
    );
}

export function saveBook(book) {
    return axios.put('/api/book/',
        book
    );
}

export function deleteBook(bookId) {
    return axios.delete('/api/book/' + bookId);
}



