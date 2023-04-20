import dayjs from 'dayjs';
import { IContactDetails } from 'app/shared/model/contact-details.model';
import { IBankingDetails } from 'app/shared/model/banking-details.model';
import { IRiskProfile } from 'app/shared/model/risk-profile.model';

export interface IIndividual {
  id?: number;
  firstName?: string;
  lastName?: string;
  initial?: string | null;
  dateOfBirth?: string;
  gender?: string | null;
  nationalId?: string | null;
  contactDetails?: IContactDetails | null;
  bankingDetails?: IBankingDetails | null;
  riskProfile?: IRiskProfile | null;
}

export const defaultValue: Readonly<IIndividual> = {};
