import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRiskProfile } from 'app/shared/model/risk-profile.model';
import { getEntities as getRiskProfiles } from 'app/entities/risk-profile/risk-profile.reducer';
import { ICondition } from 'app/shared/model/condition.model';
import { getEntity, updateEntity, createEntity, reset } from './condition.reducer';

export const ConditionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const riskProfiles = useAppSelector(state => state.riskProfile.entities);
  const conditionEntity = useAppSelector(state => state.condition.entity);
  const loading = useAppSelector(state => state.condition.loading);
  const updating = useAppSelector(state => state.condition.updating);
  const updateSuccess = useAppSelector(state => state.condition.updateSuccess);

  const handleClose = () => {
    navigate('/condition');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getRiskProfiles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...conditionEntity,
      ...values,
      riskProfile: riskProfiles.find(it => it.id.toString() === values.riskProfile.toString()),
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
          ...conditionEntity,
          riskProfile: conditionEntity?.riskProfile?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.condition.home.createOrEditLabel" data-cy="ConditionCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.condition.home.createOrEditLabel">Create or edit a Condition</Translate>
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
                  id="condition-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.condition.name')}
                id="condition-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.condition.details')}
                id="condition-details"
                name="details"
                data-cy="details"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="condition-riskProfile"
                name="riskProfile"
                data-cy="riskProfile"
                label={translate('medicalAidSystemApp.condition.riskProfile')}
                type="select"
              >
                <option value="" key="0" />
                {riskProfiles
                  ? riskProfiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/condition" replace color="info">
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

export default ConditionUpdate;
