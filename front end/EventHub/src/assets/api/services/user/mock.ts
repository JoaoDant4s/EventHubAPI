import users from '../../data/user/users.json'
import { LoginDTO } from '.';

export async function getUsers() {
    return users;
}

export async function login(loginDTO:LoginDTO) {
    return null;
}

export async function participantRegistration(){
    return null
}

export async function getByEmail(email:String) {
    return null;
}