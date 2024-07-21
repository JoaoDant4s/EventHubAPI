import { MOCK } from "../../index"
import * as mock from './mock';
import * as user from './user';

export const getUTEP = MOCK
  ? mock.getUsers()
  : user.getUsers;