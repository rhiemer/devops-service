#!/bin/bash
set -o errexit

YAML="kustomization.yaml"

YAML_TMP="$(mktemp -t )"
 python3 -c "
import yaml
with open(\"$YAML\") as f:
    y=yaml.safe_load(f)
    if not 'images' in y:
      y['images'] = []
    images = list(filter(lambda obj:(obj['name'] == 'java-deployment-template'),y['images']))
    if not images:
      y['images'].append({ 'name':'java-deployment-template','newName':'dddd','newTag':'eee' })
    else:  
      for image in images:
        image['newName'] = 'ccc'
        image['newTag'] = 'ffff'
    print(yaml.dump(y, default_flow_style=False, sort_keys=False))

" > $YAML_TMP

cat $YAML_TMP > $YAML

askçlkasçljsa
asapoiapís
askljaus
saoisapoisaoiassaaspaoisia 

aslkaspóiasṕi
saçlksaksapisa


asçjçaskjçasjçasjçjsaçsaj

saaspáoaspoi

aspoiapoiaspiassapoasipsai

saposapios
alkjklsaiouasoiasu
ssasasasasa
sapasoipsaoisapois
aspáiopaosiasposi
asopposaupasusa

xxxxxxxxxxxxxxxxxxxxxxxxxxxxx
ggggggggggggggggggggggggggggggggg
sonar
sonar
wesdsaousopdaispdiopśdiisaí
saçksaṕiosai

saassaklçksaask

assapóaspísapoiaspouas
aspoiaspasiu
sasaasklçlksa
aõsaṕoasṕassaṕisaasassa

asaṕasośapo

saçopsaoisapi
ãsásoaósasasasasa
sasaasklçlksa
sasasasasaassa

saçspáoipósiapśaio

assaasáspoṕo

saaskçalksaçljsasa[ṕas[psa]]

saasiaspisapíasṕi

sasaaspospaoṕośaposao

saljkaslosajosaiu

sasasasasa
saassasa
saposaṕoasṕoasśpaosa

salasoṕosaṕosaśapo

asçlksaçlksaçljsaçlj

jsalkaslkhaslsh
aslásçlṕoṕisi
saassasa
sssss

aoispoasisapoisa

sasaṕaosaṕosa

saçoisapoiasposuaspaou

dsśdpsdṕisdí

saçsapoasiposai

saçsakipasoiaspoias

saaskçalksaçljsa
