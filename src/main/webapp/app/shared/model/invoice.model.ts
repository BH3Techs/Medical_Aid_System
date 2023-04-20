import dayjs from 'dayjs';
import { IPolicy } from 'app/shared/model/policy.model';
import { IContactDetails } from 'app/shared/model/contact-details.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoice {
  id?: number;
  invoiceNumber?: string;
  invoiceStatus?: InvoiceStatus;
  amountPayable?: number;
  invoiceDate?: string;
  nextInvoiceDate?: string;
  invoiceAmount?: number;
  expectedPaymentDate?: string | null;
  gracePeriod?: string;
  policy?: IPolicy | null;
  contactDetails?: IContactDetails | null;
}

export const defaultValue: Readonly<IInvoice> = {};
