import { ITariff } from 'app/shared/model/tariff.model';
import { IBenefitType } from 'app/shared/model/benefit-type.model';

export interface IBenefit {
  id?: number;
  name?: string;
  description?: string;
  benefitCode?: string;
  active?: boolean;
  tariffs?: ITariff[] | null;
  benefitType?: IBenefitType | null;
}

export const defaultValue: Readonly<IBenefit> = {
  active: false,
};
