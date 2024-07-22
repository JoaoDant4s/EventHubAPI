import { MOCK } from "../../index"
import * as mock from './mock';
import * as user from './user';


export interface LoginDTO {
  email:String
  password:String
}

export const apiGetUsers = MOCK
  ? mock.getUsers()
  : user.getUsers;

export const apiLogin = MOCK
  ? mock.login
  : user.login