export interface IBankingDetails {
  id?: number;
  accountName?: string;
  accountNumber?: string;
  swiftCode?: string;
  bankName?: string;
}

export const defaultValue: Readonly<IBankingDetails> = {};
