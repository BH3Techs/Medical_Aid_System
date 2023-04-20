export interface IInvoiceLine {
  id?: number;
  paymentReason?: string | null;
  amount?: number | null;
}

export const defaultValue: Readonly<IInvoiceLine> = {};
