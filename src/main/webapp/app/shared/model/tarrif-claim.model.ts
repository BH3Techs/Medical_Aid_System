import { ICurrency } from 'app/shared/model/currency.model';
import { IClaim } from 'app/shared/model/claim.model';

export interface ITarrifClaim {
  id?: number;
  tarrifCode?: string;
  quantity?: number;
  amount?: number;
  description?: string;
  currency?: ICurrency | null;
  claim?: IClaim | null;
}

export const defaultValue: Readonly<ITarrifClaim> = {};
