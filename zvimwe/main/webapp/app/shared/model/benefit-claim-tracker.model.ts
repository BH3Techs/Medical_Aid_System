import dayjs from 'dayjs';
import { IBenefitLimit } from 'app/shared/model/benefit-limit.model';

export interface IBenefitClaimTracker {
  id?: number;
  resetDate?: string | null;
  nextPossibleClaimDate?: string | null;
  currentLimitValue?: string | null;
  currentLimitPeriod?: number | null;
  benefitLimit?: IBenefitLimit | null;
}

export const defaultValue: Readonly<IBenefitClaimTracker> = {};
