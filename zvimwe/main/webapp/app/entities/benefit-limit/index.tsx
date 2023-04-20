import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BenefitLimit from './benefit-limit';
import BenefitLimitDetail from './benefit-limit-detail';
import BenefitLimitUpdate from './benefit-limit-update';
import BenefitLimitDeleteDialog from './benefit-limit-delete-dialog';

const BenefitLimitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BenefitLimit />} />
    <Route path="new" element={<BenefitLimitUpdate />} />
    <Route path=":id">
      <Route index element={<BenefitLimitDetail />} />
      <Route path="edit" element={<BenefitLimitUpdate />} />
      <Route path="delete" element={<BenefitLimitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenefitLimitRoutes;
