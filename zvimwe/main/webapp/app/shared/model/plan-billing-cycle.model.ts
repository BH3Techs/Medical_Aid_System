import { IPlans } from 'app/shared/model/plans.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';
import { DateConfiguration } from 'app/shared/model/enumerations/date-configuration.model';

export interface IPlanBillingCycle {
  id?: number;
  periodUnit?: PeriodUnit;
  periodValue?: number;
  dateConfiguration?: DateConfiguration | null;
  billingDate?: string | null;
  plans?: IPlans | null;
}

export const defaultValue: Readonly<IPlanBillingCycle> = {};
