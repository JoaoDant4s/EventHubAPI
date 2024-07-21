import { axiosAPI } from '../../index';

let config:object = {
    headers: {
        'Accept': 'application/json;',
        'Content-Type': 'application/json',
    }
}

interface LoginDTO {
    email:String
    password:String
}

export async function login(loginDTO:LoginDTO) {
    return axiosAPI.post('/auth/', loginDTO, config);
}

export async function getUsers() {
    return axiosAPI.get('/user/', config);
}