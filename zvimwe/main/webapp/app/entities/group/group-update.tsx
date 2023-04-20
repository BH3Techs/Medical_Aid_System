import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactDetails } from 'app/shared/model/contact-details.model';
import { getEntities as getContactDetails } from 'app/entities/contact-details/contact-details.reducer';
import { IBankingDetails } from 'app/shared/model/banking-details.model';
import { getEntities as getBankingDetails } from 'app/entities/banking-details/banking-details.reducer';
import { IGroup } from 'app/shared/model/group.model';
import { getEntity, updateEntity, createEntity, reset } from './group.reducer';

export const GroupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactDetails = useAppSelector(state => state.contactDetails.entities);
  const bankingDetails = useAppSelector(state => state.bankingDetails.entities);
  const groupEntity = useAppSelector(state => state.group.entity);
  const loading = useAppSelector(state => state.group.loading);
  const updating = useAppSelector(state => state.group.updating);
  const updateSuccess = useAppSelector(state => state.group.updateSuccess);

  const handleClose = () => {
    navigate('/group');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getContactDetails({}));
    dispatch(getBankingDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...groupEntity,
      ...values,
      contactDetails: contactDetails.find(it => it.id.toString() === values.contactDetails.toString()),
      bankingDetails: bankingDetails.find(it => it.id.toString() === values.bankingDetails.toString()),
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
          ...groupEntity,
          contactDetails: groupEntity?.contactDetails?.id,
          bankingDetails: groupEntity?.bankingDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.group.home.createOrEditLabel" data-cy="GroupCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.group.home.createOrEditLabel">Create or edit a Group</Translate>
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
                  id="group-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('medicalAidSystemApp.group.name')} id="group-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('medicalAidSystemApp.group.groupType')}
                id="group-groupType"
                name="groupType"
                data-cy="groupType"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.group.dateRegistered')}
                id="group-dateRegistered"
                name="dateRegistered"
                data-cy="dateRegistered"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="group-contactDetails"
                name="contactDetails"
                data-cy="contactDetails"
                label={translate('medicalAidSystemApp.group.contactDetails')}
                type="select"
              >
                <option value="" key="0" />
                {contactDetails
                  ? contactDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="group-bankingDetails"
                name="bankingDetails"
                data-cy="bankingDetails"
                label={translate('medicalAidSystemApp.group.bankingDetails')}
                type="select"
              >
                <option value="" key="0" />
                {bankingDetails
                  ? bankingDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/group" replace color="info">
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

export default GroupUpdate;
