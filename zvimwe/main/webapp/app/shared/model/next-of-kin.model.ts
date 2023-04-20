import { IContactDetails } from 'app/shared/model/contact-details.model';
import { IPolicy } from 'app/shared/model/policy.model';

export interface INextOfKin {
  id?: number;
  name?: string;
  identifier?: string;
  contactDetails?: IContactDetails | null;
  policy?: IPolicy | null;
}

export const defaultValue: Readonly<INextOfKin> = {};
