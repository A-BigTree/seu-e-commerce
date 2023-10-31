import {useCookies} from "vue3-cookies";
const {cookies} = useCookies();

const getCookies = function (key) {
    return cookies.get(key);
}

const setCookies = function (key, value) {
    return cookies.set(key, value, '1m');
}

const removeCookies = function (key) {
    if (cookies.isKey(key)) {
        cookies.remove(key);
    }
}

const getToken = function () {
    return getCookies('token');
}

const setToken = function (value) {
    return setCookies('token', value);
}

export {
    getCookies,
    setCookies,
    removeCookies,
    getToken,
    setToken,
}