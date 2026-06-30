import { TicketStatus } from './status.enum';
import { ITicket } from './iticket.interface';

export const TICKETS_MOCK: ITicket[] = [
  {
    id: '1',
    userId: 'primer',
    title: 'primer titol',
    description: 'primera descripcio',
    status: TicketStatus.OPEN,
    comment: 'primer comment',
    mentorAssignedId: 'Alice Smith',
    createdAt: new Date('2026-05-18T10:00:00Z'),
    updatedAt: new Date('2026-05-18T10:00:00Z'),
  },
  {
    id: '2',
    userId: 'segon',
    title: 'segon titol',
    description: 'segona descripcio',
    status: TicketStatus.OPEN,
    comment: 'segon comment',
    mentorAssignedId: null,
    createdAt: new Date('2026-05-18T10:00:00Z'),
    updatedAt: new Date('2026-05-18T10:00:00Z'),
  },
  {
    id: '3',
    userId: 'tercer',
    title: 'tercer titol',
    description: 'tercera descripcio',
    status: TicketStatus.OPEN,
    comment: 'tercer comment',
    mentorAssignedId: null,
    createdAt: new Date('2026-05-18T10:00:00Z'),
    updatedAt: new Date('2026-05-18T10:00:00Z'),
  },
];
