import { ICurrency } from 'app/shared/model/currency.model';
import { WalletOwnerType } from 'app/shared/model/enumerations/wallet-owner-type.model';

export interface IWallet {
  id?: number;
  name?: string;
  balance?: number;
  ownerIdentifier?: string;
  ownerType?: WalletOwnerType;
  description?: string | null;
  active?: boolean | null;
  currency?: ICurrency | null;
}

export const defaultValue: Readonly<IWallet> = {
  active: false,
};
