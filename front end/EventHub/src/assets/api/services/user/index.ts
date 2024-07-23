import { MOCK } from "../../index"
import * as mock from './mock';
import * as user from './user';


export interface LoginDTO {
  email:String
  password:String
}

export interface participantRegistrationDTO {
  name:String
  email:String
  cpf:String
  birthDate:String
  password:String
  confirmPassword:String
}

export const apiGetUsers = MOCK
  ? mock.getUsers()
  : user.getUsers;

export const apiLogin = MOCK
  ? mock.login
  : user.login

export const apiParticipantRegistration = MOCK
  ? mock.participantRegistration
  : user.participantRegistration

export const apiGetByEmail = MOCK
  ? mock.getByEmail
  : user.getByEmail