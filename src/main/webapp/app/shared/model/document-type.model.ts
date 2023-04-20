import { IDocument } from 'app/shared/model/document.model';

export interface IDocumentType {
  id?: number;
  name?: string;
  description?: string;
  documents?: IDocument[] | null;
}

export const defaultValue: Readonly<IDocumentType> = {};
