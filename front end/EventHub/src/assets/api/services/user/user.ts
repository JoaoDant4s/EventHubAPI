import { axiosAPI } from '../../index';
import { LoginDTO } from '.';

let config:object = {
    headers: {
        'Accept': 'application/json;',
        'Content-Type': 'application/json',
    }
}

export async function login(loginDTO:LoginDTO) {
    return axiosAPI.post('/user/auth', loginDTO, config);
}

export async function getUsers() {
    return axiosAPI.get('/user/', config);
}