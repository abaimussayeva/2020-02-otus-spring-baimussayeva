/**
 * Собирает строку класса
 * @param {...any} classNames Классы
 * @return {string} Строка класса
 */
export const combineClassName = (...classNames) => classNames
    .filter(className => (className !== undefined && className !== null))
    .join(' ');

export function getAuthorsString(authors) {
    return authors.map(function (item, i) {
        if (i === authors.length - 1) {
            return item['name'];
        } else
            return item['name'] + ', ';
    })
}