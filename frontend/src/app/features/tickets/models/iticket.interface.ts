import { TicketStatus } from './status.enum';

export interface ITicket {
  id: string;
  userId: string;
  title: string;
  description: string;
  status: TicketStatus;
  comment: string | null;
  mentorAssignedId: string | null;
  createdAt: Date;
  updatedAt: Date;
}
