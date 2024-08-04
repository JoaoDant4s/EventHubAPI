import { axiosAPI } from "../../index"

export interface LoginDTO {
  email:String
  password:String
}

export interface UserDTO {
    id:Number
    name:String
    email:String
    cpf:String
    birthDate:String
    age:Number
    attractionId:Number
    participantId:Number
}

export interface ParticipantRegistrationDTO {
  email:String
  password:String
  confirmPassword:String
  name:String
  cpf:String
  birthDate:String
}

export interface ParticipantInfoDTO {
  id:Number,
  name:String,
  cpf:String,
  birthDate:String,
}

export interface AttractionInfoDTO {
  id:Number,
  name:String,
  cpf:String,
  birthDate:String,
  description:String,
  contact:String,
}

export interface AttractionDTO {
  id:Number,
  description:String
  contact:String
  userId:Number
}

export interface SaveAttractionDTO {
  description:String
  contact:String
}

export interface SaveAttractionUserDTO {
    email:String,
    password:String,
    confirmPassword:String,
    name:String,
    cpf:String,
    birthDate:String,
    attraction:SaveAttractionDTO
}

let config:object = {
    headers: {
        'Accept': 'application/json;',
        'Content-Type': 'application/json',
    }
}

export async function apiLogin(loginDTO:LoginDTO) {
    return axiosAPI.post('/user/auth', loginDTO, config);
}

export async function apiGetUsers() {
    return axiosAPI.get('/user', config);
}

export async function apiGetUserById(userId:Number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/user/'+userId, config);
}

export async function apiParticipantRegistration(participantRegistration:ParticipantRegistrationDTO) {
    return axiosAPI.post('/participant', participantRegistration, config);
}

export async function apiGetByEmail(email:String) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/user/getUserByEmail/'+email, config);
}

export async function apiDeleteUser(userId:Number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.delete('/user/'+userId, config);
}

export async function apiParticipantUpdateInfo(participantInfo:ParticipantInfoDTO) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.put('/participant/updateInfo', participantInfo, config);
}

export async function apiGetAttractionById(id:Number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/attraction/'+id, config);
}


export async function apiAttractionRegistration(attractionRegistration:SaveAttractionUserDTO) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.post('/attraction', attractionRegistration, config);
}

export async function apiAttractionUpdateInfo(attractionInfo:AttractionInfoDTO) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.put('/attraction/updateInfo', attractionInfo, config);
}

export async function apigetParticipantList() {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/participant', config);
}

export async function apigetAttractionList() {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/attraction', config);
}