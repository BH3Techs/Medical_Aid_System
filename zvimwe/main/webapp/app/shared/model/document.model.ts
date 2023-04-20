import dayjs from 'dayjs';
import { IDocumentType } from 'app/shared/model/document-type.model';
import { OwnerType } from 'app/shared/model/enumerations/owner-type.model';

export interface IDocument {
  id?: number;
  format?: string;
  name?: string;
  ownerType?: OwnerType | null;
  dateCreated?: string | null;
  validity?: boolean;
  documentType?: IDocumentType | null;
}

export const defaultValue: Readonly<IDocument> = {
  validity: false,
};
