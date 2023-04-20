import { IBenefitLimitType } from 'app/shared/model/benefit-limit-type.model';
import { IPlanBenefit } from 'app/shared/model/plan-benefit.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';

export interface IBenefitLimit {
  id?: number;
  limitValue?: string;
  limitPeriodUnit?: PeriodUnit;
  limitPeriodValue?: number;
  active?: boolean;
  benefitLimitType?: IBenefitLimitType | null;
  planBenefit?: IPlanBenefit | null;
}

export const defaultValue: Readonly<IBenefitLimit> = {
  active: false,
};
