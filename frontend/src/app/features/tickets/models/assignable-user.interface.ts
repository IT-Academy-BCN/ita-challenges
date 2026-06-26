import { Role } from "../../../core/models/role.model";

export interface AssignableUser {
  username: string;
  role: Role
}