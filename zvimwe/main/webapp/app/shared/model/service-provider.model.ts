import { IContactDetails } from 'app/shared/model/contact-details.model';
import { IClaim } from 'app/shared/model/claim.model';

export interface IServiceProvider {
  id?: number;
  name?: string;
  aHFOZNumber?: string | null;
  description?: boolean | null;
  contactDetails?: IContactDetails | null;
  claims?: IClaim[] | null;
}

export const defaultValue: Readonly<IServiceProvider> = {
  description: false,
};
