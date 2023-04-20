import dayjs from 'dayjs';
import { IContactDetails } from 'app/shared/model/contact-details.model';
import { IBankingDetails } from 'app/shared/model/banking-details.model';

export interface IGroup {
  id?: number;
  name?: string | null;
  groupType?: string | null;
  dateRegistered?: string;
  contactDetails?: IContactDetails | null;
  bankingDetails?: IBankingDetails | null;
}

export const defaultValue: Readonly<IGroup> = {};
