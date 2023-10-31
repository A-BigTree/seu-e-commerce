import {getCurrentInstance} from "vue";

const internalInstance = getCurrentInstance();
const internalData = internalInstance.appContext.config.globalProperties;

internalData.$cookies.config('1m');

const getCookies = function (key) {
    return internalData.$cookies.get(key);
}

const setCookies = function (key, value) {
    return internalData.$cookies.set(key, value);
}

const removeCookies = function (key) {
    if (internalData.$cookies.isKey(key)) {
        internalData.$cookies.remove(key);
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