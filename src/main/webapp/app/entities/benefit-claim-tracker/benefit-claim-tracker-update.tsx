import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBenefitLimit } from 'app/shared/model/benefit-limit.model';
import { getEntities as getBenefitLimits } from 'app/entities/benefit-limit/benefit-limit.reducer';
import { IBenefitClaimTracker } from 'app/shared/model/benefit-claim-tracker.model';
import { getEntity, updateEntity, createEntity, reset } from './benefit-claim-tracker.reducer';

export const BenefitClaimTrackerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const benefitLimits = useAppSelector(state => state.benefitLimit.entities);
  const benefitClaimTrackerEntity = useAppSelector(state => state.benefitClaimTracker.entity);
  const loading = useAppSelector(state => state.benefitClaimTracker.loading);
  const updating = useAppSelector(state => state.benefitClaimTracker.updating);
  const updateSuccess = useAppSelector(state => state.benefitClaimTracker.updateSuccess);

  const handleClose = () => {
    navigate('/benefit-claim-tracker');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getBenefitLimits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...benefitClaimTrackerEntity,
      ...values,
      benefitLimit: benefitLimits.find(it => it.id.toString() === values.benefitLimit.toString()),
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
          ...benefitClaimTrackerEntity,
          benefitLimit: benefitClaimTrackerEntity?.benefitLimit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.benefitClaimTracker.home.createOrEditLabel" data-cy="BenefitClaimTrackerCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.benefitClaimTracker.home.createOrEditLabel">
              Create or edit a BenefitClaimTracker
            </Translate>
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
                  id="benefit-claim-tracker-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitClaimTracker.resetDate')}
                id="benefit-claim-tracker-resetDate"
                name="resetDate"
                data-cy="resetDate"
                type="date"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitClaimTracker.nextPossibleClaimDate')}
                id="benefit-claim-tracker-nextPossibleClaimDate"
                name="nextPossibleClaimDate"
                data-cy="nextPossibleClaimDate"
                type="date"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitClaimTracker.currentLimitValue')}
                id="benefit-claim-tracker-currentLimitValue"
                name="currentLimitValue"
                data-cy="currentLimitValue"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitClaimTracker.currentLimitPeriod')}
                id="benefit-claim-tracker-currentLimitPeriod"
                name="currentLimitPeriod"
                data-cy="currentLimitPeriod"
                type="text"
              />
              <ValidatedField
                id="benefit-claim-tracker-benefitLimit"
                name="benefitLimit"
                data-cy="benefitLimit"
                label={translate('medicalAidSystemApp.benefitClaimTracker.benefitLimit')}
                type="select"
              >
                <option value="" key="0" />
                {benefitLimits
                  ? benefitLimits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/benefit-claim-tracker" replace color="info">
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

export default BenefitClaimTrackerUpdate;
