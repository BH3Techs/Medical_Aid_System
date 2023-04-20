import dayjs from 'dayjs';
import { IContactDetails } from 'app/shared/model/contact-details.model';
import { SponsorType } from 'app/shared/model/enumerations/sponsor-type.model';

export interface ISponsorAdministration {
  id?: number;
  firstName?: string;
  lastName?: string;
  initial?: string | null;
  dateOfBirth?: string;
  sponsorId?: string;
  sponsorType?: SponsorType;
  contactDetails?: IContactDetails | null;
}

export const defaultValue: Readonly<ISponsorAdministration> = {};
