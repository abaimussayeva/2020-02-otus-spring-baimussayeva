import axios from "axios";
import {ACCESS_TOKEN} from "../constants/constants";

export function axiosWithAuth(url, type, data) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        console.log('no token');
        return Promise.reject('No access token set.');
    }
    const config = {
        headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
        }
    };
    if (!type || type === 'get') {
        return axios.get(url, config);
    }
    if (type === 'post') {
        return axios.post(url, data, config);
    }
    if (type === 'put') {
        return axios.put(url, data, config);
    }
    if (type === 'delete') {
        return axios.delete(url, config);
    }
}

export function getUser() {
    return axiosWithAuth("api/user");
}

export function loadBook(bookId) {
    return axiosWithAuth('/api/book/' + bookId);
}

export function bookSearch(name) {
    return axiosWithAuth('/api/book');
}

export function loadGenres() {
    return axiosWithAuth('/api/genre');
}

export function loadAuthors() {
    return axiosWithAuth('/api/author');
}

export function loadLangs() {
    return axiosWithAuth('/api/lang');
}

export function createBook(book) {
    return axiosWithAuth('/api/book/', 'post', book);
}

export function saveBook(book) {
    return axiosWithAuth('/api/book/', 'put', book);
}

export function deleteBook(bookId) {
    return axiosWithAuth('/api/book/' + bookId, 'delete');
}

export function login(requestBody) {
    return axios.post('api/auth', requestBody, {
        headers: {
            'Content-Type': 'application/json',
        },
    });
}



