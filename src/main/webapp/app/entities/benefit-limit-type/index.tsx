import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BenefitLimitType from './benefit-limit-type';
import BenefitLimitTypeDetail from './benefit-limit-type-detail';
import BenefitLimitTypeUpdate from './benefit-limit-type-update';
import BenefitLimitTypeDeleteDialog from './benefit-limit-type-delete-dialog';

const BenefitLimitTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BenefitLimitType />} />
    <Route path="new" element={<BenefitLimitTypeUpdate />} />
    <Route path=":id">
      <Route index element={<BenefitLimitTypeDetail />} />
      <Route path="edit" element={<BenefitLimitTypeUpdate />} />
      <Route path="delete" element={<BenefitLimitTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenefitLimitTypeRoutes;
