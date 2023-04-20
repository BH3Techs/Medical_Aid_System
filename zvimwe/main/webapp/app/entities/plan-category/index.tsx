import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PlanCategory from './plan-category';
import PlanCategoryDetail from './plan-category-detail';
import PlanCategoryUpdate from './plan-category-update';
import PlanCategoryDeleteDialog from './plan-category-delete-dialog';

const PlanCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PlanCategory />} />
    <Route path="new" element={<PlanCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<PlanCategoryDetail />} />
      <Route path="edit" element={<PlanCategoryUpdate />} />
      <Route path="delete" element={<PlanCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlanCategoryRoutes;
