import { axiosAPI } from '../../index';
import { LoginDTO, participantRegistrationDTO } from '.';

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
    return axiosAPI.get('/user', config);
}

export async function participantRegistration(participantRegistration:participantRegistrationDTO) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.post('/participant', participantRegistration, config);
}