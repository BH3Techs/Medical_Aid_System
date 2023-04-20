export interface IContactDetails {
  id?: number;
  primaryPhoneNumber?: string;
  secondaryPhoneNumber?: string | null;
  emailAddress?: string | null;
  physicalAddress?: string;
  whatsappNumber?: string | null;
}

export const defaultValue: Readonly<IContactDetails> = {};
