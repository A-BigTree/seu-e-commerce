const {ref} = require("@dcloudio/uni-mp-vue");
const formatTime = date => {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hour = date.getHours();
    const minute = date.getMinutes();
    const second = date.getSeconds();
    return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':');
};

const formatNumber = n => {
    n = n.toString();
    return n[1] ? n : '0' + n;
};

const formatHtml = content => {
    content = content.replace(/\<img/gi, '<img style="width:100% !important;height:auto !important;margin:0;display:flex;" ');
    content = content.replace(/\<td/gi, '<td  cellspacing="0" cellpadding="0" border="0" style="display:block;vertical-align:top;margin: 0px; padding: 0px; border: 0px;outline-width:0px;" ');
    content = content.replace(/width=/gi, 'sss=');
    content = content.replace(/height=/gi, 'sss=');
    content = content.replace(/ \/\>/gi, ' style="max-width:100% !important;height:auto !important;margin:0;display:block;" \/\>');
    return content;
};

/**
 * 获取链接上的参数
 */
const getUrlKey = (name) => {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || ['', ''])[1]
        .replace(/\+/g, '%20')) || null
}

/**
 * 移除购物车Tabbar的数字
 */
const removeTabBadge = () => {
    let pl = '';
    // #ifdef MP-WEIXIN
    pl = 'mp';
    // #endif
    uni.removeTabBarBadge({
        index: pl === 'mp' ? 3 : 2
    }).then(r => {
    });
}

const checkEmail = (email) => {
    const reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/
    return reg.test(email);
}

const countDown = (second, callBack) => {
    const counter = ref(0)
    let timer = -1
    counter.value = second;
    timer = window.setInterval(() => {
        counter.value--;
        callBack(counter.value);
        if (counter.value === 0) {
            clearInterval(timer);
        }
    }, 1000);
}

module.exports = {
    formatTime,
    getUrlKey,
    formatHtml,
    removeTabBadge,
    checkEmail,
    countDown
};