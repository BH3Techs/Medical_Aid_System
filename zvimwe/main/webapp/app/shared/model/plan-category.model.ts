import dayjs from 'dayjs';
import { IPlans } from 'app/shared/model/plans.model';

export interface IPlanCategory {
  id?: number;
  name?: string;
  description?: string | null;
  dateCreated?: string | null;
  active?: boolean;
  plans?: IPlans[] | null;
}

export const defaultValue: Readonly<IPlanCategory> = {
  active: false,
};
