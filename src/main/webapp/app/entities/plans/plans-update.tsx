import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPolicy } from 'app/shared/model/policy.model';
import { getEntities as getPolicies } from 'app/entities/policy/policy.reducer';
import { IPlanCategory } from 'app/shared/model/plan-category.model';
import { getEntities as getPlanCategories } from 'app/entities/plan-category/plan-category.reducer';
import { IPlans } from 'app/shared/model/plans.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';
import { getEntity, updateEntity, createEntity, reset } from './plans.reducer';

export const PlansUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const policies = useAppSelector(state => state.policy.entities);
  const planCategories = useAppSelector(state => state.planCategory.entities);
  const plansEntity = useAppSelector(state => state.plans.entity);
  const loading = useAppSelector(state => state.plans.loading);
  const updating = useAppSelector(state => state.plans.updating);
  const updateSuccess = useAppSelector(state => state.plans.updateSuccess);
  const periodUnitValues = Object.keys(PeriodUnit);

  const handleClose = () => {
    navigate('/plans');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPolicies({}));
    dispatch(getPlanCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...plansEntity,
      ...values,
      policies: mapIdList(values.policies),
      planCategory: planCategories.find(it => it.id.toString() === values.planCategory.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          coverPeriodUnit: 'DAY',
          ...plansEntity,
          policies: plansEntity?.policies?.map(e => e.id.toString()),
          planCategory: plansEntity?.planCategory?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.plans.home.createOrEditLabel" data-cy="PlansCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.plans.home.createOrEditLabel">Create or edit a Plans</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="plans-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.planCode')}
                id="plans-planCode"
                name="planCode"
                data-cy="planCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.name')}
                id="plans-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.basePremium')}
                id="plans-basePremium"
                name="basePremium"
                data-cy="basePremium"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.coverAmount')}
                id="plans-coverAmount"
                name="coverAmount"
                data-cy="coverAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.coverPeriodUnit')}
                id="plans-coverPeriodUnit"
                name="coverPeriodUnit"
                data-cy="coverPeriodUnit"
                type="select"
              >
                {periodUnitValues.map(periodUnit => (
                  <option value={periodUnit} key={periodUnit}>
                    {translate('medicalAidSystemApp.PeriodUnit.' + periodUnit)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.coverPeriodValue')}
                id="plans-coverPeriodValue"
                name="coverPeriodValue"
                data-cy="coverPeriodValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.active')}
                id="plans-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.plans.policy')}
                id="plans-policy"
                data-cy="policy"
                type="select"
                multiple
                name="policies"
              >
                <option value="" key="0" />
                {policies
                  ? policies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="plans-planCategory"
                name="planCategory"
                data-cy="planCategory"
                label={translate('medicalAidSystemApp.plans.planCategory')}
                type="select"
              >
                <option value="" key="0" />
                {planCategories
                  ? planCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plans" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PlansUpdate;
