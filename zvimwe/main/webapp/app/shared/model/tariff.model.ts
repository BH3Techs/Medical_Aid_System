import { ICurrency } from 'app/shared/model/currency.model';
import { IBenefit } from 'app/shared/model/benefit.model';

export interface ITariff {
  id?: number;
  name?: string;
  price?: number | null;
  active?: boolean;
  currency?: ICurrency | null;
  benefit?: IBenefit | null;
}

export const defaultValue: Readonly<ITariff> = {
  active: false,
};
