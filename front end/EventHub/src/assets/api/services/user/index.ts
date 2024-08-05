import { axiosAPI } from "../../index"

export interface LoginDTO {
  email:string,
  password:string
}

export interface UserDTO {
    id:number,
    name:string,
    email:string,
    cpf:string,
    birthDate:string,
    age:number,
    attractionId:number,
    participantId:number,
    promoter:Boolean
}

export interface ParticipantRegistrationDTO {
  email:string,
  password:string,
  confirmPassword:string,
  name:string,
  cpf:string,
  birthDate:string
}

export interface ParticipantInfoDTO {
  id:number,
  name:string,
  cpf:string,
  birthDate:string,
}

export interface AttractionInfoDTO {
  id:number,
  name:string,
  cpf:string,
  birthDate:string,
  description:string,
  contact:string,
}

export interface AttractionDTO {
  id:number,
  description:string,
  contact:string,
  userId:number
}

export interface SaveAttractionDTO {
  description:string,
  contact:string
}

export interface SaveAttractionUserDTO {
    email:string,
    password:string,
    confirmPassword:string,
    name:string,
    cpf:string,
    birthDate:string,
    attraction:SaveAttractionDTO
}

export interface SaveCreditCardDTO {
    cardNumber:string,
    expiration:string,
    cardHolderName:string,
    participantId:number
}

export interface CreditCardDTO {
    id:number
    cardHolderName:string
    cardNumber:string
    expiration:string
    participantId:number
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

export async function apiGetUserById(userId:number) {
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

export async function apiGetByEmail(email:string) {
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

export async function apiDeleteUser(userId:number) {
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

export async function apiGetAttractionById(id:number) {
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

export async function apiGetParticipantList() {
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

export async function apiGetAttractionList() {
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

export async function apiGetPromoterList() {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/user/promoter', config);
}


export async function apiPromoterRegistration(participantRegistration:ParticipantRegistrationDTO) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.post('/user/promoter', participantRegistration, config);
}

export async function apiSaveCreditCard(saveCreditCardDTO:SaveCreditCardDTO) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.post('/creditCard', saveCreditCardDTO, config);
}

export async function apiGetByParticipantId(participantId:number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/creditCard/getByParticipantId/'+participantId, config);
}

export async function apiDeleteCreditCrad(creditCardId:number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.delete('/creditCard/'+creditCardId, config);
}