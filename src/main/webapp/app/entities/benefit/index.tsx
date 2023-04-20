import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Benefit from './benefit';
import BenefitDetail from './benefit-detail';
import BenefitUpdate from './benefit-update';
import BenefitDeleteDialog from './benefit-delete-dialog';

const BenefitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Benefit />} />
    <Route path="new" element={<BenefitUpdate />} />
    <Route path=":id">
      <Route index element={<BenefitDetail />} />
      <Route path="edit" element={<BenefitUpdate />} />
      <Route path="delete" element={<BenefitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenefitRoutes;
