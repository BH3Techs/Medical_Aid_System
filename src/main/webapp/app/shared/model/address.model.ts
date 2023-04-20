export interface IAddress {
  id?: number;
  streetAddress?: string | null;
  surburb?: string | null;
  province?: string | null;
  city?: string | null;
  country?: string | null;
}

export const defaultValue: Readonly<IAddress> = {};
