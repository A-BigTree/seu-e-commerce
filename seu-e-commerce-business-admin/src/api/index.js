import {http} from '@/utils/http';

const uploadImage = (imageFile, prefix) => {
    const formData = new FormData();
    formData.append("image", imageFile);
    formData.append("prefix", prefix);
    let result = "";
    const params = {
        url: "/file/manage/image/upload",
        isFile: true,
        data: formData,
        callBack: (res) => {
            result = res.data;
        }
    };
    http(params);
    return result;
}

export {
    uploadImage,
}