import { IBenefitLimit } from 'app/shared/model/benefit-limit.model';

export interface IBenefitLimitType {
  id?: number;
  name?: string;
  active?: boolean;
  benefitLimits?: IBenefitLimit[] | null;
}

export const defaultValue: Readonly<IBenefitLimitType> = {
  active: false,
};
