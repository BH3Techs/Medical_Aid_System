import { ITariff } from 'app/shared/model/tariff.model';
import { IWallet } from 'app/shared/model/wallet.model';
import { ITarrifClaim } from 'app/shared/model/tarrif-claim.model';
import { IPlans } from 'app/shared/model/plans.model';

export interface ICurrency {
  id?: number;
  currencyName?: string;
  currencyCode?: string;
  active?: boolean | null;
  tariffs?: ITariff[] | null;
  wallets?: IWallet[] | null;
  tarrifClaims?: ITarrifClaim[] | null;
  plans?: IPlans | null;
}

export const defaultValue: Readonly<ICurrency> = {
  active: false,
};
