import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BenefitType from './benefit-type';
import BenefitTypeDetail from './benefit-type-detail';
import BenefitTypeUpdate from './benefit-type-update';
import BenefitTypeDeleteDialog from './benefit-type-delete-dialog';

const BenefitTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BenefitType />} />
    <Route path="new" element={<BenefitTypeUpdate />} />
    <Route path=":id">
      <Route index element={<BenefitTypeDetail />} />
      <Route path="edit" element={<BenefitTypeUpdate />} />
      <Route path="delete" element={<BenefitTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenefitTypeRoutes;
