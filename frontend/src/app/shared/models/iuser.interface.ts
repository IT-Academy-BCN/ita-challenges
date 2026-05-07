import { Role } from "../../core/models/role.enum";

export interface IUser {
    username: string,
    role: Role,
}
