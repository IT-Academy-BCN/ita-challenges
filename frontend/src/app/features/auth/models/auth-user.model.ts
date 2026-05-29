import { Role } from "../../../core/models/role.enum";

export interface AuthUser {
  username: string;
  avatarUrl: string;
  token?: string;
  role?: Role;
}
